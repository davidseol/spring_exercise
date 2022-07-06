package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import assembler.Assembler;
import spring.ChangePasswordService;
import spring.DuplicateMemberException;
import spring.MemberNotFoundException;
import spring.MemberRegisterService;
import spring.RegisterRequest;
import spring.WrongIdPasswordException;


public class MainForAssembler {
    public static void main(String[] args) throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            System.out.println("????? ????????:");
            String command = reader.readLine();
            if(command.equalsIgnoreCase("exit")) {
                System.out.println("????????.");
                break;
            }
            if(command.startsWith("new")) {
                processNewCommand(command.split(" "));
                continue;
            } else if(command.startsWith("change")) {
                processChangeCommand(command.split(" "));
                continue;
            }
            printHelp();
        }
    }

    private static Assembler assembler = new Assembler();

    private static void processNewCommand(String[] arg) {
        if(arg.length != 5) {
            printHelp();
            return;
        }
        MemberRegisterService regSvc = assembler.getMemberRegisterService();
        RegisterRequest req = new RegisterRequest();
        req.setEmail(arg[1]);
        req.setName(arg[2]);
        req.setPassword(arg[3]);
        req.setConfirmPassword(arg[4]);

        if(!req.isPasswordEqualToConfirmPassword()) {
            System.out.println("????? ????? ??????? ??????.\n");
            return;
        }
        try{
            regSvc.regist(req);
            System.out.println("?????????.\n");
        } catch(DuplicateMemberException e) {
            System.out.println("??? ??????? ?????????.\n");
        }
    }

    private static void processChangeCommand(String[] arg) {
        if(arg.length != 4) {
            printHelp();
            return;
        }
        ChangePasswordService changePwdSvc =
                assembler.getChangePasswordService();
        try {
            changePwdSvc.changePassword(arg[1], arg[2], arg[3]);
            System.out.println("????? ??????????.\n");
        } catch(MemberNotFoundException e) {
            System.out.println("???????? ??? ?????????.\n");
        } catch(WrongIdPasswordException e) {
            System.out.println("?????? ????? ??????? ??????.\n");
        }
    }

    private static void printHelp() {
        System.out.println();
        System.out.println("????? ????????. ??? ????? ?????? ????????.");
        System.out.println("????? ????:");
        System.out.println("new ????? ??? ??? ??????");
        System.out.println("change ????? ?????? ??????");
        System.out.println();
    }
}