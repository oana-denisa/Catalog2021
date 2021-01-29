import { element, by, ElementFinder } from 'protractor';

export class DisciplinaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-disciplina div table .btn-danger'));
  title = element.all(by.css('jhi-disciplina div h2#page-heading span')).first();
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

export class DisciplinaUpdatePage {
  pageTitle = element(by.id('jhi-disciplina-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  denumireInput = element(by.id('field_denumire'));
  descriereInput = element(by.id('field_descriere'));
  puncteCreditInput = element(by.id('field_puncteCredit'));
  anDeStudiuInput = element(by.id('field_anDeStudiu'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDenumireInput(denumire: string): Promise<void> {
    await this.denumireInput.sendKeys(denumire);
  }

  async getDenumireInput(): Promise<string> {
    return await this.denumireInput.getAttribute('value');
  }

  async setDescriereInput(descriere: string): Promise<void> {
    await this.descriereInput.sendKeys(descriere);
  }

  async getDescriereInput(): Promise<string> {
    return await this.descriereInput.getAttribute('value');
  }

  async setPuncteCreditInput(puncteCredit: string): Promise<void> {
    await this.puncteCreditInput.sendKeys(puncteCredit);
  }

  async getPuncteCreditInput(): Promise<string> {
    return await this.puncteCreditInput.getAttribute('value');
  }

  async setAnDeStudiuInput(anDeStudiu: string): Promise<void> {
    await this.anDeStudiuInput.sendKeys(anDeStudiu);
  }

  async getAnDeStudiuInput(): Promise<string> {
    return await this.anDeStudiuInput.getAttribute('value');
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

export class DisciplinaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-disciplina-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-disciplina'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
