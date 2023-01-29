import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MapComponentsPage, MapDeleteDialog, MapUpdatePage } from './map.page-object';

const expect = chai.expect;

describe('Map e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let mapComponentsPage: MapComponentsPage;
  let mapUpdatePage: MapUpdatePage;
  let mapDeleteDialog: MapDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Maps', async () => {
    await navBarPage.goToEntity('map');
    mapComponentsPage = new MapComponentsPage();
    await browser.wait(ec.visibilityOf(mapComponentsPage.title), 5000);
    expect(await mapComponentsPage.getTitle()).to.eq('Maps');
    await browser.wait(ec.or(ec.visibilityOf(mapComponentsPage.entities), ec.visibilityOf(mapComponentsPage.noResult)), 1000);
  });

  it('should load create Map page', async () => {
    await mapComponentsPage.clickOnCreateButton();
    mapUpdatePage = new MapUpdatePage();
    expect(await mapUpdatePage.getPageTitle()).to.eq('Create or edit a Map');
    await mapUpdatePage.cancel();
  });

  it('should create and save Maps', async () => {
    const nbButtonsBeforeCreate = await mapComponentsPage.countDeleteButtons();

    await mapComponentsPage.clickOnCreateButton();

    await promise.all([
      mapUpdatePage.setPlayerPositionXInput('5'),
      mapUpdatePage.setPlayerPositionYInput('5'),
      mapUpdatePage.setPlayerPositionZInput('5'),
    ]);

    expect(await mapUpdatePage.getPlayerPositionXInput()).to.eq('5', 'Expected playerPositionX value to be equals to 5');
    expect(await mapUpdatePage.getPlayerPositionYInput()).to.eq('5', 'Expected playerPositionY value to be equals to 5');
    expect(await mapUpdatePage.getPlayerPositionZInput()).to.eq('5', 'Expected playerPositionZ value to be equals to 5');

    await mapUpdatePage.save();
    expect(await mapUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await mapComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Map', async () => {
    const nbButtonsBeforeDelete = await mapComponentsPage.countDeleteButtons();
    await mapComponentsPage.clickOnLastDeleteButton();

    mapDeleteDialog = new MapDeleteDialog();
    expect(await mapDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Map?');
    await mapDeleteDialog.clickOnConfirmButton();

    expect(await mapComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
