import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

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
  NbCardModule,
} from '@nebular/theme';
import { AppRoutingModule } from '../app-routing.module';

import { HomePageComponent } from './components/pages/home-page/home-page.component';
import { SkeletonComponent } from './components/navigation/skeleton/skeleton.component';

@NgModule({
  declarations: [SkeletonComponent, HomePageComponent],
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
    NbCardModule,
  ],
  exports: [SkeletonComponent],
})
export class ThemeModule {}
