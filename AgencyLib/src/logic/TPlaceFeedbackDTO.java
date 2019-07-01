/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;

/**
 *
 * @author bruno
 */
public class TPlaceFeedbackDTO implements Serializable{
    private Integer id;
    private int score;
    
    public TPlaceFeedbackDTO() {
    }

    public TPlaceFeedbackDTO(Integer id, int score) {
        this.id = id;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    
    @Override
    public String toString() {
        return "TFeedbackDTO{" + "id=" + id + ", score=" + score + '}';
    }

    
}
