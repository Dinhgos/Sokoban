import { element, by, ElementFinder } from 'protractor';

export class MapComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-map div table .btn-danger'));
  title = element.all(by.css('jhi-map div h2#page-heading span')).first();
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

export class MapUpdatePage {
  pageTitle = element(by.id('jhi-map-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  playerPositionXInput = element(by.id('field_playerPositionX'));
  playerPositionYInput = element(by.id('field_playerPositionY'));
  playerPositionZInput = element(by.id('field_playerPositionZ'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
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

export class MapDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-map-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-map'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
