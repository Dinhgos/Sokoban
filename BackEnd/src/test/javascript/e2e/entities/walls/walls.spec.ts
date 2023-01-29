import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { WallsComponentsPage, WallsDeleteDialog, WallsUpdatePage } from './walls.page-object';

const expect = chai.expect;

describe('Walls e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let wallsComponentsPage: WallsComponentsPage;
  let wallsUpdatePage: WallsUpdatePage;
  let wallsDeleteDialog: WallsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Walls', async () => {
    await navBarPage.goToEntity('walls');
    wallsComponentsPage = new WallsComponentsPage();
    await browser.wait(ec.visibilityOf(wallsComponentsPage.title), 5000);
    expect(await wallsComponentsPage.getTitle()).to.eq('Walls');
    await browser.wait(ec.or(ec.visibilityOf(wallsComponentsPage.entities), ec.visibilityOf(wallsComponentsPage.noResult)), 1000);
  });

  it('should load create Walls page', async () => {
    await wallsComponentsPage.clickOnCreateButton();
    wallsUpdatePage = new WallsUpdatePage();
    expect(await wallsUpdatePage.getPageTitle()).to.eq('Create or edit a Walls');
    await wallsUpdatePage.cancel();
  });

  it('should create and save Walls', async () => {
    const nbButtonsBeforeCreate = await wallsComponentsPage.countDeleteButtons();

    await wallsComponentsPage.clickOnCreateButton();

    await promise.all([
      wallsUpdatePage.setPositionXInput('5'),
      wallsUpdatePage.setPositionYInput('5'),
      wallsUpdatePage.setPositionZInput('5'),
      wallsUpdatePage.mapSelectLastOption(),
    ]);

    expect(await wallsUpdatePage.getPositionXInput()).to.eq('5', 'Expected positionX value to be equals to 5');
    expect(await wallsUpdatePage.getPositionYInput()).to.eq('5', 'Expected positionY value to be equals to 5');
    expect(await wallsUpdatePage.getPositionZInput()).to.eq('5', 'Expected positionZ value to be equals to 5');

    await wallsUpdatePage.save();
    expect(await wallsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await wallsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Walls', async () => {
    const nbButtonsBeforeDelete = await wallsComponentsPage.countDeleteButtons();
    await wallsComponentsPage.clickOnLastDeleteButton();

    wallsDeleteDialog = new WallsDeleteDialog();
    expect(await wallsDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Walls?');
    await wallsDeleteDialog.clickOnConfirmButton();

    expect(await wallsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
