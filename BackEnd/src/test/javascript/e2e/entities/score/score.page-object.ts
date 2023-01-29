import { element, by, ElementFinder } from 'protractor';

export class ScoreComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-score div table .btn-danger'));
  title = element.all(by.css('jhi-score div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class ScoreUpdatePage {
  pageTitle = element(by.id('jhi-score-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  valueInput = element(by.id('field_value'));
  dateInput = element(by.id('field_date'));

  mapSelect = element(by.id('field_map'));
  playerSelect = element(by.id('field_player'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setValueInput(value: string): Promise<void> {
    await this.valueInput.sendKeys(value);
  }

  async getValueInput(): Promise<string> {
    return await this.valueInput.getAttribute('value');
  }

  async setDateInput(date: string): Promise<void> {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput(): Promise<string> {
    return await this.dateInput.getAttribute('value');
  }

  async mapSelectLastOption(): Promise<void> {
    await this.mapSelect.all(by.tagName('option')).last().click();
  }

  async mapSelectOption(option: string): Promise<void> {
    await this.mapSelect.sendKeys(option);
  }

  getMapSelect(): ElementFinder {
    return this.mapSelect;
  }

  async getMapSelectedOption(): Promise<string> {
    return await this.mapSelect.element(by.css('option:checked')).getText();
  }

  async playerSelectLastOption(): Promise<void> {
    await this.playerSelect.all(by.tagName('option')).last().click();
  }

  async playerSelectOption(option: string): Promise<void> {
    await this.playerSelect.sendKeys(option);
  }

  getPlayerSelect(): ElementFinder {
    return this.playerSelect;
  }

  async getPlayerSelectedOption(): Promise<string> {
    return await this.playerSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ScoreDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-score-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-score'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
