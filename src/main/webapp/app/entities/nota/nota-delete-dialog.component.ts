import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INota } from 'app/shared/model/nota.model';
import { NotaService } from './nota.service';

@Component({
  templateUrl: './nota-delete-dialog.component.html',
})
export class NotaDeleteDialogComponent {
  nota?: INota;

  constructor(protected notaService: NotaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.notaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('notaListModification');
      this.activeModal.close();
    });
  }
}
