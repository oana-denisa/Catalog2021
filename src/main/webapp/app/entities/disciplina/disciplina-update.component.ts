import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDisciplina, Disciplina } from 'app/shared/model/disciplina.model';
import { DisciplinaService } from './disciplina.service';

@Component({
  selector: 'jhi-disciplina-update',
  templateUrl: './disciplina-update.component.html',
})
export class DisciplinaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    denumire: [null, [Validators.required, Validators.maxLength(15)]],
    descriere: [null, [Validators.required, Validators.minLength(30)]],
    puncteCredit: [null, [Validators.required, Validators.min(2), Validators.max(6)]],
    anDeStudiu: [null, [Validators.required, Validators.min(1), Validators.max(4)]],
  });

  constructor(protected disciplinaService: DisciplinaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ disciplina }) => {
      this.updateForm(disciplina);
    });
  }

  updateForm(disciplina: IDisciplina): void {
    this.editForm.patchValue({
      id: disciplina.id,
      denumire: disciplina.denumire,
      descriere: disciplina.descriere,
      puncteCredit: disciplina.puncteCredit,
      anDeStudiu: disciplina.anDeStudiu,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const disciplina = this.createFromForm();
    if (disciplina.id !== undefined) {
      this.subscribeToSaveResponse(this.disciplinaService.update(disciplina));
    } else {
      this.subscribeToSaveResponse(this.disciplinaService.create(disciplina));
    }
  }

  private createFromForm(): IDisciplina {
    return {
      ...new Disciplina(),
      id: this.editForm.get(['id'])!.value,
      denumire: this.editForm.get(['denumire'])!.value,
      descriere: this.editForm.get(['descriere'])!.value,
      puncteCredit: this.editForm.get(['puncteCredit'])!.value,
      anDeStudiu: this.editForm.get(['anDeStudiu'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDisciplina>>): void {
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
}
