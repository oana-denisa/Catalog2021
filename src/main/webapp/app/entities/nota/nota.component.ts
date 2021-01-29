import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INota } from 'app/shared/model/nota.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { NotaService } from './nota.service';
import { NotaDeleteDialogComponent } from './nota-delete-dialog.component';

@Component({
  selector: 'jhi-nota',
  templateUrl: './nota.component.html',
})
export class NotaComponent implements OnInit, OnDestroy {
  notas: INota[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected notaService: NotaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.notas = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.notaService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<INota[]>) => this.paginateNotas(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.notas = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNotas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INota): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNotas(): void {
    this.eventSubscriber = this.eventManager.subscribe('notaListModification', () => this.reset());
  }

  delete(nota: INota): void {
    const modalRef = this.modalService.open(NotaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nota = nota;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateNotas(data: INota[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.notas.push(data[i]);
      }
    }
  }
}
