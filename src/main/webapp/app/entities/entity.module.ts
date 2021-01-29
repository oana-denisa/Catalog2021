import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'disciplina',
        loadChildren: () => import('./disciplina/disciplina.module').then(m => m.CatalogDisciplinaModule),
      },
      {
        path: 'nota',
        loadChildren: () => import('./nota/nota.module').then(m => m.CatalogNotaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class CatalogEntityModule {}
