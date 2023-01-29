import { element, by, ElementFinder } from 'protractor';

export class SaveComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-save div table .btn-danger'));
  title = element.all(by.css('jhi-save div h2#page-heading span')).first();
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

export class SaveUpdatePage {
  pageTitle = element(by.id('jhi-save-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  movesInput = element(by.id('field_moves'));
  timeInput = element(by.id('field_time'));
  playerPositionXInput = element(by.id('field_playerPositionX'));
  playerPositionYInput = element(by.id('field_playerPositionY'));
  playerPositionZInput = element(by.id('field_playerPositionZ'));

  playerSelect = element(by.id('field_player'));
  mapSelect = element(by.id('field_map'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setMovesInput(moves: string): Promise<void> {
    await this.movesInput.sendKeys(moves);
  }

  async getMovesInput(): Promise<string> {
    return await this.movesInput.getAttribute('value');
  }

  async setTimeInput(time: string): Promise<void> {
    await this.timeInput.sendKeys(time);
  }

  async getTimeInput(): Promise<string> {
    return await this.timeInput.getAttribute('value');
  }

  async setPlayerPositionXInput(playerPositionX: string): Promise<void> {
    await this.playerPositionXInput.sendKeys(playerPositionX);
  }

  async getPlayerPositionXInput(): Promise<string> {
    return await this.playerPositionXInput.getAttribute('value');
  }

  async setPlayerPositionYInput(playerPositionY: string): Promise<void> {
    await this.playerPositionYInput.sendKeys(playerPositionY);
  }

  async getPlayerPositionYInput(): Promise<string> {
    return await this.playerPositionYInput.getAttribute('value');
  }

  async setPlayerPositionZInput(playerPositionZ: string): Promise<void> {
    await this.playerPositionZInput.sendKeys(playerPositionZ);
  }

  async getPlayerPositionZInput(): Promise<string> {
    return await this.playerPositionZInput.getAttribute('value');
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

export class SaveDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-save-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-save'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
