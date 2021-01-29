import { element, by, ElementFinder } from 'protractor';

export class NotaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-nota div table .btn-danger'));
  title = element.all(by.css('jhi-nota div h2#page-heading span')).first();
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
    return this.title.getAttribute('jhiTranslate');
  }
}

export class NotaUpdatePage {
  pageTitle = element(by.id('jhi-nota-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  numarPuncteInput = element(by.id('field_numarPuncte'));
  notaInput = element(by.id('field_nota'));
  dataInput = element(by.id('field_data'));

  userSelect = element(by.id('field_user'));
  disciplinaSelect = element(by.id('field_disciplina'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNumarPuncteInput(numarPuncte: string): Promise<void> {
    await this.numarPuncteInput.sendKeys(numarPuncte);
  }

  async getNumarPuncteInput(): Promise<string> {
    return await this.numarPuncteInput.getAttribute('value');
  }

  async setNotaInput(nota: string): Promise<void> {
    await this.notaInput.sendKeys(nota);
  }

  async getNotaInput(): Promise<string> {
    return await this.notaInput.getAttribute('value');
  }

  async setDataInput(data: string): Promise<void> {
    await this.dataInput.sendKeys(data);
  }

  async getDataInput(): Promise<string> {
    return await this.dataInput.getAttribute('value');
  }

  async userSelectLastOption(): Promise<void> {
    await this.userSelect.all(by.tagName('option')).last().click();
  }

  async userSelectOption(option: string): Promise<void> {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption(): Promise<string> {
    return await this.userSelect.element(by.css('option:checked')).getText();
  }

  async disciplinaSelectLastOption(): Promise<void> {
    await this.disciplinaSelect.all(by.tagName('option')).last().click();
  }

  async disciplinaSelectOption(option: string): Promise<void> {
    await this.disciplinaSelect.sendKeys(option);
  }

  getDisciplinaSelect(): ElementFinder {
    return this.disciplinaSelect;
  }

  async getDisciplinaSelectedOption(): Promise<string> {
    return await this.disciplinaSelect.element(by.css('option:checked')).getText();
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

export class NotaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-nota-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-nota'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
