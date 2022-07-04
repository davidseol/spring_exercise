package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.AppCtx;
import spring.*;
import spring.MemberListPrinter;
import spring.MemberInfoPrinter;
import spring.MemberPrinter;

public class MainForSpring {
    private static ApplicationContext ctx = null;
    public static void main(String[] args) throws IOException {
        ctx = new AnnotationConfigApplicationContext(AppCtx.class);

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            System.out.println("��ɾ �Է��ϼ���:");
            String command = reader.readLine();
            if(command.equalsIgnoreCase("exit")) {
                System.out.println("�����մϴ�.");
                break;
            }
            if(command.startsWith("new")) {
                processNewCommmand(command.split(" "));
                continue;
            } else if(command.startsWith("change")) {
                processNewCommmand(command.split(" "));
                continue;
            } else if(command.equals("list")) {
                processNewCommmand();
                continue;
            } else if(command.startsWith("info")) {
                processListCommand(command.split(" "));
                continue;
            } else if(command.equals("version")) {
                processVersionCommand();
                continue;
            }
            printHelp();

        }
    }
    private static void processNewCommmand(String[] arg) {
        if(arg.length != 5) {
            printHelp();
            return;
        }
        MemberRegisterService regSvc =
                ctx.getBean("memberRegSvc",MemberRegisterService.class);
        RegisterRequest req = new RegisterRequest();
        req.setEmail(arg[1]);

        if(!req.isPasswordEqualToConfirmPassword()) {
            System.out.println("��ȣ�� Ȯ���� ��ġ���� �ʽ��ϴ�.\n");
            return;
        }
        try{
            regSvc.regist(req);
            System.out.println("����߽��ϴ�.\n");
        } catch(AlreadyExistingMemberException e) {
            System.out.println("�̹� �����ϴ� �̸����Դϴ�.\n");
        }
    }
    private static processChangeCommand(String[] arg) {
        if (arg.length != 4) {
            printHelp();
            return;
        }
        ChangePasswordService changePwdSvc =
                ctx.getBean("changePwdSvc", ChangePasswordService.class);
        try {
            changePwdSvc.changePassword(arg[1], arg[2], arg[3]);
            System.out.println("��ȣ�� �����߽��ϴ�.\n");
        } catch (MemberNotFoundException e) {
            System.out.println("�������� �ʴ� �̸����Դϴ�.\n");
        } catch (IdPasswordNotMatchingException e) {
            System.out.println("�̸��ϰ� ��ȣ�� ��ġ���� �ʽ��ϴ�.\n");
        }

        }
        private static void processListCommand() {
            MemberListPrinter listPrinter =
                    ctx.getBean("listPrinter", MemberListPrinter.class);
            listPrinter.printAll();
        }
        private static void processInfoCommand(String[] arg) {
        if(arg.length != 2) {
            printHelp();
            return;
        }
        MemberInfoPrinter infoPrinter =
                ctx.getBean("infoPrinter", MemberInfoPrinter.class);
        infoPrinter.printMemberInfo(arg[1]);
        }
        private static void processVersionCommand() {
        VersionPrinter versionPrinter =
                ctx.getBean("versionPrinter", VersionPrinter.class);
        versionPrinter.print();
        }

}
