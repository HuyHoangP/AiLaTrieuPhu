package com.hhp.ailatrieuphu.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.hhp.ailatrieuphu.database.entity.Money;
import com.hhp.ailatrieuphu.database.entity.Question;

import java.util.List;
@Dao
public interface QuestionDAO {
    @Query("SELECT *FROM (SELECT *FROM Question quiz WHERE quiz.level LIKE 1 ORDER BY RANDOM() LIMIT 1)\n" +
            "UNION\n" +
            "SELECT *FROM (SELECT *FROM Question quiz WHERE quiz.level LIKE 2 ORDER BY RANDOM() LIMIT 1)\n" +
            "UNION\n" +
            "SELECT *FROM (SELECT *FROM Question quiz WHERE quiz.level LIKE 3 ORDER BY RANDOM() LIMIT 1)\n" +
            "UNION\n" +
            "SELECT *FROM (SELECT *FROM Question quiz WHERE quiz.level LIKE 4 ORDER BY RANDOM() LIMIT 1)\n" +
            "UNION\n" +
            "SELECT *FROM (SELECT *FROM Question quiz WHERE quiz.level LIKE 5 ORDER BY RANDOM() LIMIT 1)\n" +
            "UNION\n" +
            "SELECT *FROM (SELECT *FROM Question quiz WHERE quiz.level LIKE 6 ORDER BY RANDOM() LIMIT 1)\n" +
            "UNION\n" +
            "SELECT *FROM (SELECT *FROM Question quiz WHERE quiz.level LIKE 7 ORDER BY RANDOM() LIMIT 1)\n" +
            "UNION\n" +
            "SELECT *FROM (SELECT *FROM Question quiz WHERE quiz.level LIKE 8 ORDER BY RANDOM() LIMIT 1)\n" +
            "UNION\n" +
            "SELECT *FROM (SELECT *FROM Question quiz WHERE quiz.level LIKE 9 ORDER BY RANDOM() LIMIT 1)\n" +
            "UNION\n" +
            "SELECT *FROM (SELECT *FROM Question quiz WHERE quiz.level LIKE 10 ORDER BY RANDOM() LIMIT 1)\n" +
            "UNION\n" +
            "SELECT *FROM (SELECT *FROM Question quiz WHERE quiz.level LIKE 11 ORDER BY RANDOM() LIMIT 1)\n" +
            "UNION\n" +
            "SELECT *FROM (SELECT *FROM Question quiz WHERE quiz.level LIKE 12 ORDER BY RANDOM() LIMIT 1)\n" +
            "UNION\n" +
            "SELECT *FROM (SELECT *FROM Question quiz WHERE quiz.level LIKE 13 ORDER BY RANDOM() LIMIT 1)\n" +
            "UNION\n" +
            "SELECT *FROM (SELECT *FROM Question quiz WHERE quiz.level LIKE 14 ORDER BY RANDOM() LIMIT 1)\n" +
            "UNION\n" +
            "SELECT *FROM (SELECT *FROM Question quiz WHERE quiz.level LIKE 15 ORDER BY RANDOM() LIMIT 1)\n" +
            "ORDER BY level ASC")
    List<Question> getAllQuestion();
    @Query("SELECT * FROM Money")
    LiveData<Money> getMoney();
    @Query("UPDATE Money SET Amount =:amount")
    void updateMoney(int amount);
    @Query("SELECT * FROM question quiz WHERE quiz.level LIKE 6 \n" +
            "UNION\n" +
            "SELECT * FROM question quiz WHERE quiz.level LIKE 7 \n" +
            "UNION\n" +
            "SELECT * FROM question quiz WHERE quiz.level LIKE 8\n" +
            "UNION\n" +
            "SELECT * FROM question quiz WHERE quiz.level LIKE 9 \n" +
            "UNION\n" +
            "SELECT * FROM question quiz WHERE quiz.level LIKE 10 \n" +
            "UNION\n" +
            "SELECT * FROM question quiz WHERE quiz.level LIKE 11 \n" +
            "UNION\n" +
            "SELECT * FROM question quiz WHERE quiz.level LIKE 12 \n" +
            "UNION\n" +
            "SELECT * FROM question quiz WHERE quiz.level LIKE 13 \n" +
            "UNION\n" +
            "SELECT * FROM question quiz WHERE quiz.level LIKE 14 \n" +
            "UNION\n" +
            "SELECT * FROM question quiz WHERE quiz.level LIKE 15")
    List<Question> getRankedQuestions();
}
