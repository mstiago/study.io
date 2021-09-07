import { Component, OnInit } from '@angular/core';
import { NbMenuItem, NbSidebarService } from '@nebular/theme';

@Component({
  selector: 'app-skeleton',
  templateUrl: './skeleton.component.html',
  styleUrls: ['./skeleton.component.scss'],
})
export class SkeletonComponent implements OnInit {
  items: NbMenuItem[] = [
    {
      title: 'Inicio',
      icon: 'home-outline',
      link: '',
      home: true,
    },
    {
      title: 'ESPAÇO DE ESTUDO',
      group: true,
      icon: 'cube-outline',
    },
    {
      title: 'Workspace',
      icon: 'browser-outline',
      expanded: true,
      children: [
        {
          title: 'Novo workspace',
          link: '',
          icon: 'plus-circle-outline',
        },
      ],
    },
    { title: 'Sair', link: '', icon: 'log-out-outline' },
  ];

  constructor(private sidebarService: NbSidebarService) {}

  ngOnInit(): void {
    return;
  }

  /**
   * Handle with sidebar toggle
   */
  toggleCompact(): void {
    this.sidebarService.toggle(true);
  }
}
