package nl.tue.s2id90.group10;


import java.util.ArrayList;
import java.util.List;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.draughts.player.DraughtsPlayer;
import org10x10.dam.game.Move;

/**
 * A simple draughts player that plays the first moves that comes to mind
 * and values all moves with value 0.
 * @author huub
 */
public class OptimisticPlayer extends DraughtsPlayer {
    //the possible states after opponent made one move
    List<GameNode> currentChildren;
    private static int depthLimit = 6;
    public OptimisticPlayer() {
        super(UninformedPlayer.class.getResource("resources/optimist.png"));
        currentChildren = new ArrayList<GameNode>();
    }
    @Override
    /** @return a random move **/
    public Move getMove(DraughtsState s) {
        GameNode currentNode = null;
        GameNode bestNode = null;
        float max = 0;
        //first move in the game
        if(currentChildren.size() == 0){
            currentNode = new GameNode(s.clone(),null);
        }else{
            //find the new state
            for(GameNode node : currentChildren){
                    if(sameState(s,node.getGameState())){
                        currentNode = node;
                    }
            } 
        }
        //.getSucceesors() automatically check possible moves and generate child nodes
        for(GameNode node : currentNode.getSuccessors()){
            //update the depth
            node.setDepth(1);
            //pick the best move
            if( max < alphaBetaMin(node,-1,100000) ){
                bestNode = node;
            }
        }
        currentChildren = bestNode.getSuccessors();
        return bestNode.getMoveToIt();
    }

    public float alphaBetaMax(GameNode node, float alpha, float beta){
        //reached the leaf
        if(node.depth == depthLimit){
            return node.getRating();
        }
        List<GameNode> newNodes = node.getSuccessors();
        //no possible move
        if( newNodes.isEmpty() ){
            return node.getRating();
        }
        // go deeper 
        for( GameNode n : newNodes ){
            n.setDepth(node.depth+1);
            alpha = Math.max(alpha, alphaBetaMin(n,alpha, beta));
            if ( alpha >= beta ){
                return beta;
            }
        }
        return alpha;
    }
    
    public float alphaBetaMin(GameNode node, float alpha, float beta){
        
        if(node.depth == depthLimit){
            return node.getRating();
        }
        List<GameNode> newNodes = node.getSuccessors();
        if( newNodes.isEmpty() ){
            return node.getRating();
        }
        for( GameNode n : newNodes ){
            n.setDepth(node.depth+1);
            beta = Math.min(beta, alphaBetaMax(n,alpha, beta));
            if ( beta <= alpha ){
                return alpha;
            }
        }
        return alpha;
    }
    //check whether s1 and s2 represent the same state
    public boolean sameState( DraughtsState s1, DraughtsState s2){
        for(int i = 1;i <= 50 ; i++){
           if(s1.getPiece(i) != s2.getPiece(i)){
               return false;
           }
        }
        return true;
    }
    public void setDepthLimit(){
        
    }
   
    @Override
    public Integer getValue() {
        return 0;
    }
}
