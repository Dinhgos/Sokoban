import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { GoalPositionComponentsPage, GoalPositionDeleteDialog, GoalPositionUpdatePage } from './goal-position.page-object';

const expect = chai.expect;

describe('GoalPosition e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let goalPositionComponentsPage: GoalPositionComponentsPage;
  let goalPositionUpdatePage: GoalPositionUpdatePage;
  let goalPositionDeleteDialog: GoalPositionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load GoalPositions', async () => {
    await navBarPage.goToEntity('goal-position');
    goalPositionComponentsPage = new GoalPositionComponentsPage();
    await browser.wait(ec.visibilityOf(goalPositionComponentsPage.title), 5000);
    expect(await goalPositionComponentsPage.getTitle()).to.eq('Goal Positions');
    await browser.wait(
      ec.or(ec.visibilityOf(goalPositionComponentsPage.entities), ec.visibilityOf(goalPositionComponentsPage.noResult)),
      1000
    );
  });

  it('should load create GoalPosition page', async () => {
    await goalPositionComponentsPage.clickOnCreateButton();
    goalPositionUpdatePage = new GoalPositionUpdatePage();
    expect(await goalPositionUpdatePage.getPageTitle()).to.eq('Create or edit a Goal Position');
    await goalPositionUpdatePage.cancel();
  });

  it('should create and save GoalPositions', async () => {
    const nbButtonsBeforeCreate = await goalPositionComponentsPage.countDeleteButtons();

    await goalPositionComponentsPage.clickOnCreateButton();

    await promise.all([
      goalPositionUpdatePage.setPositionXInput('5'),
      goalPositionUpdatePage.setPositionYInput('5'),
      goalPositionUpdatePage.setPositionZInput('5'),
      goalPositionUpdatePage.mapSelectLastOption(),
    ]);

    expect(await goalPositionUpdatePage.getPositionXInput()).to.eq('5', 'Expected positionX value to be equals to 5');
    expect(await goalPositionUpdatePage.getPositionYInput()).to.eq('5', 'Expected positionY value to be equals to 5');
    expect(await goalPositionUpdatePage.getPositionZInput()).to.eq('5', 'Expected positionZ value to be equals to 5');

    await goalPositionUpdatePage.save();
    expect(await goalPositionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await goalPositionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last GoalPosition', async () => {
    const nbButtonsBeforeDelete = await goalPositionComponentsPage.countDeleteButtons();
    await goalPositionComponentsPage.clickOnLastDeleteButton();

    goalPositionDeleteDialog = new GoalPositionDeleteDialog();
    expect(await goalPositionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Goal Position?');
    await goalPositionDeleteDialog.clickOnConfirmButton();

    expect(await goalPositionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
