import { Component, OnInit } from '@angular/core';

import { Match } from '../../model/match';
import { MatchService } from '../../service/match.service';

@Component({
  selector: 'app-match-list',
  templateUrl: './match-list.component.html',
  styleUrls: ['./match-list.component.css']
})
export class MatchListComponent {
  matches: Map<string, number>;

  countriesText: string

  devicesText: string;

  constructor(private matchService: MatchService) {}

  onSearch() {
    let countriesList = []
    if (!!this.countriesText) {
      countriesList = this.countriesText.split(",")
    }
    let devicesList = []
    if (!!this.devicesText) {
      devicesList = this.devicesText.split(",")
    }
    this.matchService.findMatch(countriesList, devicesList).subscribe(result => this.matches = result);
  }
}
