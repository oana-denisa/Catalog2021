import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDisciplina } from 'app/shared/model/disciplina.model';
import { DisciplinaService } from './disciplina.service';

@Component({
  templateUrl: './disciplina-delete-dialog.component.html',
})
export class DisciplinaDeleteDialogComponent {
  disciplina?: IDisciplina;

  constructor(
    protected disciplinaService: DisciplinaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.disciplinaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('disciplinaListModification');
      this.activeModal.close();
    });
  }
}
