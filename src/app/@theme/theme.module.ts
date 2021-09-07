import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SkeletonComponent } from './components/navigation/skeleton/skeleton.component';

import { NbEvaIconsModule } from '@nebular/eva-icons';
import {
  NbActionsModule,
  NbIconModule,
  NbSearchModule,
  NbSidebarModule,
  NbLayoutModule,
  NbButtonModule,
  NbMenuModule,
  NbUserModule,
} from '@nebular/theme';
import { AppRoutingModule } from '../app-routing.module';

@NgModule({
  declarations: [SkeletonComponent],
  imports: [
    CommonModule,
    AppRoutingModule,
    NbEvaIconsModule,
    NbIconModule,
    NbActionsModule,
    NbSearchModule,
    NbSidebarModule,
    NbLayoutModule,
    NbButtonModule,
    NbMenuModule,
    NbUserModule,
  ],
  exports: [SkeletonComponent],
})
export class ThemeModule {}
