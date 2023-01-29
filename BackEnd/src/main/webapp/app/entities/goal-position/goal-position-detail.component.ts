import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGoalPosition } from 'app/shared/model/goal-position.model';

@Component({
  selector: 'jhi-goal-position-detail',
  templateUrl: './goal-position-detail.component.html',
})
export class GoalPositionDetailComponent implements OnInit {
  goalPosition: IGoalPosition | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ goalPosition }) => (this.goalPosition = goalPosition));
  }

  previousState(): void {
    window.history.back();
  }
}
