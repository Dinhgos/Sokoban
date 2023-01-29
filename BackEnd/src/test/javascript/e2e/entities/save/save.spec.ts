import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SaveComponentsPage, SaveDeleteDialog, SaveUpdatePage } from './save.page-object';

const expect = chai.expect;

describe('Save e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let saveComponentsPage: SaveComponentsPage;
  let saveUpdatePage: SaveUpdatePage;
  let saveDeleteDialog: SaveDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Saves', async () => {
    await navBarPage.goToEntity('save');
    saveComponentsPage = new SaveComponentsPage();
    await browser.wait(ec.visibilityOf(saveComponentsPage.title), 5000);
    expect(await saveComponentsPage.getTitle()).to.eq('Saves');
    await browser.wait(ec.or(ec.visibilityOf(saveComponentsPage.entities), ec.visibilityOf(saveComponentsPage.noResult)), 1000);
  });

  it('should load create Save page', async () => {
    await saveComponentsPage.clickOnCreateButton();
    saveUpdatePage = new SaveUpdatePage();
    expect(await saveUpdatePage.getPageTitle()).to.eq('Create or edit a Save');
    await saveUpdatePage.cancel();
  });

  it('should create and save Saves', async () => {
    const nbButtonsBeforeCreate = await saveComponentsPage.countDeleteButtons();

    await saveComponentsPage.clickOnCreateButton();

    await promise.all([
      saveUpdatePage.setMovesInput('5'),
      saveUpdatePage.setTimeInput('5'),
      saveUpdatePage.setPlayerPositionXInput('5'),
      saveUpdatePage.setPlayerPositionYInput('5'),
      saveUpdatePage.setPlayerPositionZInput('5'),
      saveUpdatePage.playerSelectLastOption(),
      saveUpdatePage.mapSelectLastOption(),
    ]);

    expect(await saveUpdatePage.getMovesInput()).to.eq('5', 'Expected moves value to be equals to 5');
    expect(await saveUpdatePage.getTimeInput()).to.eq('5', 'Expected time value to be equals to 5');
    expect(await saveUpdatePage.getPlayerPositionXInput()).to.eq('5', 'Expected playerPositionX value to be equals to 5');
    expect(await saveUpdatePage.getPlayerPositionYInput()).to.eq('5', 'Expected playerPositionY value to be equals to 5');
    expect(await saveUpdatePage.getPlayerPositionZInput()).to.eq('5', 'Expected playerPositionZ value to be equals to 5');

    await saveUpdatePage.save();
    expect(await saveUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await saveComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Save', async () => {
    const nbButtonsBeforeDelete = await saveComponentsPage.countDeleteButtons();
    await saveComponentsPage.clickOnLastDeleteButton();

    saveDeleteDialog = new SaveDeleteDialog();
    expect(await saveDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Save?');
    await saveDeleteDialog.clickOnConfirmButton();

    expect(await saveComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
