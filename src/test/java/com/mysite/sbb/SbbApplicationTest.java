package com.mysite.sbb;

import com.mysite.sbb.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTest {

    @Autowired
    private QuestionService questionService;

    @Test
    void testJpa() {
        for(int i = 1; i <= 300; i++) {
            questionService.create("test case : " + i, "content", null);
        }
    }

}