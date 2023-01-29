import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BoxesComponentsPage, BoxesDeleteDialog, BoxesUpdatePage } from './boxes.page-object';

const expect = chai.expect;

describe('Boxes e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let boxesComponentsPage: BoxesComponentsPage;
  let boxesUpdatePage: BoxesUpdatePage;
  let boxesDeleteDialog: BoxesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Boxes', async () => {
    await navBarPage.goToEntity('boxes');
    boxesComponentsPage = new BoxesComponentsPage();
    await browser.wait(ec.visibilityOf(boxesComponentsPage.title), 5000);
    expect(await boxesComponentsPage.getTitle()).to.eq('Boxes');
    await browser.wait(ec.or(ec.visibilityOf(boxesComponentsPage.entities), ec.visibilityOf(boxesComponentsPage.noResult)), 1000);
  });

  it('should load create Boxes page', async () => {
    await boxesComponentsPage.clickOnCreateButton();
    boxesUpdatePage = new BoxesUpdatePage();
    expect(await boxesUpdatePage.getPageTitle()).to.eq('Create or edit a Boxes');
    await boxesUpdatePage.cancel();
  });

  it('should create and save Boxes', async () => {
    const nbButtonsBeforeCreate = await boxesComponentsPage.countDeleteButtons();

    await boxesComponentsPage.clickOnCreateButton();

    await promise.all([
      boxesUpdatePage.setPositionXInput('5'),
      boxesUpdatePage.setPositionYInput('5'),
      boxesUpdatePage.setPositionZInput('5'),
      boxesUpdatePage.saveSelectLastOption(),
      boxesUpdatePage.mapSelectLastOption(),
    ]);

    expect(await boxesUpdatePage.getPositionXInput()).to.eq('5', 'Expected positionX value to be equals to 5');
    expect(await boxesUpdatePage.getPositionYInput()).to.eq('5', 'Expected positionY value to be equals to 5');
    expect(await boxesUpdatePage.getPositionZInput()).to.eq('5', 'Expected positionZ value to be equals to 5');

    await boxesUpdatePage.save();
    expect(await boxesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await boxesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Boxes', async () => {
    const nbButtonsBeforeDelete = await boxesComponentsPage.countDeleteButtons();
    await boxesComponentsPage.clickOnLastDeleteButton();

    boxesDeleteDialog = new BoxesDeleteDialog();
    expect(await boxesDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Boxes?');
    await boxesDeleteDialog.clickOnConfirmButton();

    expect(await boxesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
