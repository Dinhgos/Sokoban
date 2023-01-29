import { element, by, ElementFinder } from 'protractor';

export class WallsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-walls div table .btn-danger'));
  title = element.all(by.css('jhi-walls div h2#page-heading span')).first();
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

export class WallsUpdatePage {
  pageTitle = element(by.id('jhi-walls-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  positionXInput = element(by.id('field_positionX'));
  positionYInput = element(by.id('field_positionY'));
  positionZInput = element(by.id('field_positionZ'));

  mapSelect = element(by.id('field_map'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setPositionXInput(positionX: string): Promise<void> {
    await this.positionXInput.sendKeys(positionX);
  }

  async getPositionXInput(): Promise<string> {
    return await this.positionXInput.getAttribute('value');
  }

  async setPositionYInput(positionY: string): Promise<void> {
    await this.positionYInput.sendKeys(positionY);
  }

  async getPositionYInput(): Promise<string> {
    return await this.positionYInput.getAttribute('value');
  }

  async setPositionZInput(positionZ: string): Promise<void> {
    await this.positionZInput.sendKeys(positionZ);
  }

  async getPositionZInput(): Promise<string> {
    return await this.positionZInput.getAttribute('value');
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

export class WallsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-walls-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-walls'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
