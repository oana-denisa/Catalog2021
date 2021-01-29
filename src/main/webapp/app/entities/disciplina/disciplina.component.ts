import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDisciplina } from 'app/shared/model/disciplina.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DisciplinaService } from './disciplina.service';
import { DisciplinaDeleteDialogComponent } from './disciplina-delete-dialog.component';

@Component({
  selector: 'jhi-disciplina',
  templateUrl: './disciplina.component.html',
})
export class DisciplinaComponent implements OnInit, OnDestroy {
  disciplinas: IDisciplina[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected disciplinaService: DisciplinaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.disciplinas = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.disciplinaService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IDisciplina[]>) => this.paginateDisciplinas(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.disciplinas = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDisciplinas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDisciplina): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDisciplinas(): void {
    this.eventSubscriber = this.eventManager.subscribe('disciplinaListModification', () => this.reset());
  }

  delete(disciplina: IDisciplina): void {
    const modalRef = this.modalService.open(DisciplinaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.disciplina = disciplina;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDisciplinas(data: IDisciplina[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.disciplinas.push(data[i]);
      }
    }
  }
}
