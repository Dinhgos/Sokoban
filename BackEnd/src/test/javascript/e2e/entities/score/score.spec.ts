import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ScoreComponentsPage, ScoreDeleteDialog, ScoreUpdatePage } from './score.page-object';

const expect = chai.expect;

describe('Score e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let scoreComponentsPage: ScoreComponentsPage;
  let scoreUpdatePage: ScoreUpdatePage;
  let scoreDeleteDialog: ScoreDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Scores', async () => {
    await navBarPage.goToEntity('score');
    scoreComponentsPage = new ScoreComponentsPage();
    await browser.wait(ec.visibilityOf(scoreComponentsPage.title), 5000);
    expect(await scoreComponentsPage.getTitle()).to.eq('Scores');
    await browser.wait(ec.or(ec.visibilityOf(scoreComponentsPage.entities), ec.visibilityOf(scoreComponentsPage.noResult)), 1000);
  });

  it('should load create Score page', async () => {
    await scoreComponentsPage.clickOnCreateButton();
    scoreUpdatePage = new ScoreUpdatePage();
    expect(await scoreUpdatePage.getPageTitle()).to.eq('Create or edit a Score');
    await scoreUpdatePage.cancel();
  });

  it('should create and save Scores', async () => {
    const nbButtonsBeforeCreate = await scoreComponentsPage.countDeleteButtons();

    await scoreComponentsPage.clickOnCreateButton();

    await promise.all([
      scoreUpdatePage.setValueInput('5'),
      scoreUpdatePage.setDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      scoreUpdatePage.mapSelectLastOption(),
      scoreUpdatePage.playerSelectLastOption(),
    ]);

    expect(await scoreUpdatePage.getValueInput()).to.eq('5', 'Expected value value to be equals to 5');
    expect(await scoreUpdatePage.getDateInput()).to.contain('2001-01-01T02:30', 'Expected date value to be equals to 2000-12-31');

    await scoreUpdatePage.save();
    expect(await scoreUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await scoreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Score', async () => {
    const nbButtonsBeforeDelete = await scoreComponentsPage.countDeleteButtons();
    await scoreComponentsPage.clickOnLastDeleteButton();

    scoreDeleteDialog = new ScoreDeleteDialog();
    expect(await scoreDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Score?');
    await scoreDeleteDialog.clickOnConfirmButton();

    expect(await scoreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
