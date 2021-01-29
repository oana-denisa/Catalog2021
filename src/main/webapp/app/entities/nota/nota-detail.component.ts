import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INota } from 'app/shared/model/nota.model';

@Component({
  selector: 'jhi-nota-detail',
  templateUrl: './nota-detail.component.html',
})
export class NotaDetailComponent implements OnInit {
  nota: INota | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nota }) => (this.nota = nota));
  }

  previousState(): void {
    window.history.back();
  }
}
