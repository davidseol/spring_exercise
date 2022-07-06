package spring;

import jdk.swing.interop.SwingInterOpUtils;

public class MemberPrinter {
    public void print(Member member) {
        System.out.printf(
                "??? ????: ?????=%d, ?????=%s, ???=%s, ?????=%tF\n"
                member.getId(), member.getEmail(),
                member.getName(), member.getRegisterDateTime());
    }
}