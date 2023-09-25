package org.example;

import org.example.services.Member;
import org.example.services.OSBBMemberCriteria;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        OSBBMemberCriteria osbbMemberCriteria = new OSBBMemberCriteria();

        List<Member> members = osbbMemberCriteria.getMembersWithAutoNotAllowed();

        for (Member member : members) {
            System.out.println(member);
        }

        osbbMemberCriteria.close();
    }
}

