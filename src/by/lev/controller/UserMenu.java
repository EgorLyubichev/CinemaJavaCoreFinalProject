package by.lev.controller;

import by.lev.service.inputChecks.InputCorrectness;


import static by.lev.Constant.INCORRECT_INPUT;

public class UserMenu {
    public void showUserMenu() {
        System.out.println("- - -|  CACTUS CINEMA  |- - -");
        System.out.println("- - -| ГЛАВНАЯ СТРАНИЦА|- - -");
        System.out.println("<1> - предстоящие фильмы");
        System.out.println("<2> - купить билет");
        System.out.println("<3> - купленные билеты");
        System.out.println("<4> - отменить билет");
        System.out.println("<5> - сменить пароль");
        System.out.println("<0> - выход из программы");

        int inputValue = new InputCorrectness().inputCorrectValueFromZeroToFive();
        if (inputValue == -1){
            System.out.println(INCORRECT_INPUT.getMessage());
            System.out.println();
            showUserMenu();
        }
        switch (inputValue) {
            case 1:
                new UserMenuAction().showUpcomingSessions();
                System.out.println();
                showUserMenu();
                break;
            case 2:
                new UserMenuAction.BuyingATicket().buyATicket();
                break;
            case 3:
                new UserMenuAction().showUserTickets();
                showUserMenu();
                break;
            case 4:
                new UserMenuAction().cancelTheTicket();
                showUserMenu();
                break;
            case 5:
                new UserMenuAction().changePassword();
                showUserMenu();
                break;
            case 0:
                System.exit(0);
        }
    }
}
