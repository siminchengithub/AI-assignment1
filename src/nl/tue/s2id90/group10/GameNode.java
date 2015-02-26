/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.tue.s2id90.group10;

import java.util.ArrayList;
import java.util.List;
import nl.tue.s2id90.draughts.DraughtsState;
import org10x10.dam.game.Move;

/**
 *
 * @author Chen
 */
public class GameNode {
    private DraughtsState state ;
    private List<GameNode> branches;
    private float score;
    //the move from its parent to itself
    private Move moveToIt;
    public int depth;
    public GameNode(DraughtsState state, Move move){
        this.state = state;
        branches = new ArrayList<GameNode>();
        score = 0;
        this.moveToIt = move;
    }
    
    public DraughtsState getGameState(){
        return state;
    }
    
    public Move getMoveToIt(){
        return this.moveToIt;
    }
    //return children
    public List<GameNode> getSuccessors(){
        if( branches.isEmpty() ){
            List<Move> moves = state.getMoves();
            for( Move m : moves ){
                DraughtsState newstate = state.clone();
                newstate.doMove(m);
                branches.add(new GameNode(newstate,m));
            }
        }
        return branches;
    }
     //herustic function
    public float getRating(){
        for(int i : state.getPieces()){
            if(i == 0){
                
                
            }
        }
        return 0;
    }
    
    public void setDepth(int d){
        this.depth = d;
    }
}
