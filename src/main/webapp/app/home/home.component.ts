import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';

import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';
import { ScrollToPlugin } from 'gsap/ScrollToPlugin';

gsap.registerPlugin(ScrollTrigger, ScrollToPlugin);

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account | null = null;

  private readonly destroy$ = new Subject<void>();
  constructor(private accountService: AccountService, private router: Router) {}

  gsapAnim(): void {
    const mainTimeline = gsap.timeline();

    mainTimeline
      // .from(
      //   '.buttons',
      //   {
      //     duration: 0.8,
      //     opacity: 0.1,
      //     ease: 'bounce',
      //     y: -50,
      //     stagger: 0.3,
      //   },
      //   '<0.2'
      // )
      .to('.main__heading', {
        duration: 0.9,
        opacity: 1,
        clipPath: 'polygon(0 0, 100% 0%, 100% 100%, 0 100%)',
        ease: 'sine.out()',
      })
      .to(
        '.main__picture',
        {
          duration: 0.9,
          opacity: 1,
          clipPath: 'circle(70.7% at 50% 50%)',
          ease: 'slow(0.7, 0.7, false)',
        },
        '<0.1'
      );
  }

  ngOnInit(): void {
    this.gsapAnim();
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
