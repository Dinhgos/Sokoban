import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PlayerComponentsPage, PlayerDeleteDialog, PlayerUpdatePage } from './player.page-object';

const expect = chai.expect;

describe('Player e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let playerComponentsPage: PlayerComponentsPage;
  let playerUpdatePage: PlayerUpdatePage;
  let playerDeleteDialog: PlayerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Players', async () => {
    await navBarPage.goToEntity('player');
    playerComponentsPage = new PlayerComponentsPage();
    await browser.wait(ec.visibilityOf(playerComponentsPage.title), 5000);
    expect(await playerComponentsPage.getTitle()).to.eq('Players');
    await browser.wait(ec.or(ec.visibilityOf(playerComponentsPage.entities), ec.visibilityOf(playerComponentsPage.noResult)), 1000);
  });

  it('should load create Player page', async () => {
    await playerComponentsPage.clickOnCreateButton();
    playerUpdatePage = new PlayerUpdatePage();
    expect(await playerUpdatePage.getPageTitle()).to.eq('Create or edit a Player');
    await playerUpdatePage.cancel();
  });

  it('should create and save Players', async () => {
    const nbButtonsBeforeCreate = await playerComponentsPage.countDeleteButtons();

    await playerComponentsPage.clickOnCreateButton();

    await promise.all([
      playerUpdatePage.setNameInput('name'),
      playerUpdatePage.setPasswordInput('password'),
      playerUpdatePage.setLevelInput('5'),
    ]);

    expect(await playerUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await playerUpdatePage.getPasswordInput()).to.eq('password', 'Expected Password value to be equals to password');
    expect(await playerUpdatePage.getLevelInput()).to.eq('5', 'Expected level value to be equals to 5');

    await playerUpdatePage.save();
    expect(await playerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await playerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Player', async () => {
    const nbButtonsBeforeDelete = await playerComponentsPage.countDeleteButtons();
    await playerComponentsPage.clickOnLastDeleteButton();

    playerDeleteDialog = new PlayerDeleteDialog();
    expect(await playerDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Player?');
    await playerDeleteDialog.clickOnConfirmButton();

    expect(await playerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
