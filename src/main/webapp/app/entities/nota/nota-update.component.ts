import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { INota, Nota } from 'app/shared/model/nota.model';
import { NotaService } from './nota.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IDisciplina } from 'app/shared/model/disciplina.model';
import { DisciplinaService } from 'app/entities/disciplina/disciplina.service';

type SelectableEntity = IUser | IDisciplina;

@Component({
  selector: 'jhi-nota-update',
  templateUrl: './nota-update.component.html',
})
export class NotaUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  disciplinas: IDisciplina[] = [];

  editForm = this.fb.group({
    id: [],
    numarPuncte: [null, [Validators.required, Validators.min(1), Validators.max(100)]],
    nota: [null, [Validators.required, Validators.min(1), Validators.max(10)]],
    data: [null, [Validators.required]],
    user: [],
    disciplina: [],
  });

  constructor(
    protected notaService: NotaService,
    protected userService: UserService,
    protected disciplinaService: DisciplinaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nota }) => {
      if (!nota.id) {
        const today = moment().startOf('day');
        nota.data = today;
      }

      this.updateForm(nota);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.disciplinaService.query().subscribe((res: HttpResponse<IDisciplina[]>) => (this.disciplinas = res.body || []));
    });
  }

  updateForm(nota: INota): void {
    this.editForm.patchValue({
      id: nota.id,
      numarPuncte: nota.numarPuncte,
      nota: nota.nota,
      data: nota.data ? nota.data.format(DATE_TIME_FORMAT) : null,
      user: nota.user,
      disciplina: nota.disciplina,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nota = this.createFromForm();
    if (nota.id !== undefined) {
      this.subscribeToSaveResponse(this.notaService.update(nota));
    } else {
      this.subscribeToSaveResponse(this.notaService.create(nota));
    }
  }

  private createFromForm(): INota {
    return {
      ...new Nota(),
      id: this.editForm.get(['id'])!.value,
      numarPuncte: this.editForm.get(['numarPuncte'])!.value,
      nota: this.editForm.get(['nota'])!.value,
      data: this.editForm.get(['data'])!.value ? moment(this.editForm.get(['data'])!.value, DATE_TIME_FORMAT) : undefined,
      user: this.editForm.get(['user'])!.value,
      disciplina: this.editForm.get(['disciplina'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INota>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
