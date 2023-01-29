import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'player',
        loadChildren: () => import('./player/player.module').then(m => m.MyAppPlayerModule),
      },
      {
        path: 'score',
        loadChildren: () => import('./score/score.module').then(m => m.MyAppScoreModule),
      },
      {
        path: 'walls',
        loadChildren: () => import('./walls/walls.module').then(m => m.MyAppWallsModule),
      },
      {
        path: 'goal-position',
        loadChildren: () => import('./goal-position/goal-position.module').then(m => m.MyAppGoalPositionModule),
      },
      {
        path: 'boxes',
        loadChildren: () => import('./boxes/boxes.module').then(m => m.MyAppBoxesModule),
      },
      {
        path: 'map',
        loadChildren: () => import('./map/map.module').then(m => m.MyAppMapModule),
      },
      {
        path: 'save',
        loadChildren: () => import('./save/save.module').then(m => m.MyAppSaveModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class MyAppEntityModule {}
