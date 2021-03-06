package hearc.othello.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.File;

import hearc.othello.R;
import hearc.othello.model.AI.PlayerAI;
import hearc.othello.model.Game;
import hearc.othello.model.GameBoard;
import hearc.othello.model.Move;
import hearc.othello.model.Player;
import hearc.othello.model.PlayerHuman;
import hearc.othello.tools.Tools;
import hearc.othello.view.dialog.DialogListener;
import hearc.othello.view.dialog.MainDialog;
import hearc.othello.view.dialog.NewOrEndDialog;

public class GameActivity extends AppCompatActivity implements Button.OnClickListener, SensorEventListener, DialogListener {
    /*      Graphical elements      */
    private TableLayout tableLayout;
    private TextView textTime;
    private TextView textScore;

    /*      Static text elements    */
    //TODO : in strings file
    private static final String timeText = "Time : ";
    private static final String scoreText = " - ";

    /*      Logical elements        */
    private Game game;

    /*      Sensors elements        */
    private SensorManager sensorManager;
    private float accel;        // acceleration apart from gravity
    private float accelCurrent; // current acceleration including gravity
    private float accelLast;    // last acceleration including gravity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Init Activity
        initGameActivity();

        //Initiate the game
        if(!getIntent().hasExtra("load_file"))
            initGame();
        else{
            String fileName = (String) getIntent().getExtras().get("load_file");
            loadGame(fileName);
        }

        //TODO : launch thread for time
        /*CustomTimerTask myTask = new CustomTimerTask();
        Timer myTimer = new Timer();
        myTimer.schedule(myTask, 3000, 1500);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    private void initGameActivity(){
        setContentView(R.layout.layout_game);

        //Getting graphical elements
        tableLayout = (TableLayout) findViewById(R.id.graphic_gameboard);
        textTime = (TextView) findViewById(R.id.time);
        textScore = (TextView) findViewById(R.id.score);

        //Init sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        accel = 0.00f;
        accelCurrent = SensorManager.GRAVITY_EARTH;
        accelLast = SensorManager.GRAVITY_EARTH;
    }

    private void saveGame(boolean show){
        File file = new File(getFilesDir(), "game_"+ game.getMode());
        Tools.writeSerializableInFile(this, game, file);

        if(show)
            Tools.Toast(this, "Game saved");
    }

    private void loadGame(String fileName) {
        int mode = getIntent().getIntExtra("Type", -1);

        File file = new File(getFilesDir(), fileName + mode);
        Game game = (Game) Tools.readSerializable(file);
        this.game = game;

        updateGraphic();

    }

    public void initGame(){
        //Get init Infos from previous activity
        int mode = getIntent().getIntExtra("Type", -1);
        String nameP1 = getIntent().getStringExtra("NameP1");
        String nameP2 = getIntent().getStringExtra("NameP2");

        //Init logical objects
        Player p1 = new PlayerHuman(0, nameP1);
        Player p2;
        if(mode == R.id.vsIA) {
            int IALevel = getIntent().getIntExtra("IA_level2", -1);
            p2 = new PlayerAI(1, IALevel);
        }
        else
            p2 = new PlayerHuman(1, nameP2);

        game = new Game(mode, p1, p2);

        updateGraphic();
        saveGame(false);
    }

    public void endGame(){
        new NewOrEndDialog(this, this).show();
    }

    /***
     * OnClick for the gameBoard's cells
     * @param v : View from the cell where the player want to add a piece
     */
    @Override
    public void onClick(View v) {
        //Get move from cell
        ImageView cell = (ImageView) v;
        Move nextMove = getMoveFromCase(cell);

        //Play the move in the game
        game.playMove(nextMove);

        //Update graphics
        updateGraphic();
        switch (game.getState()){
            case Game.NO_MOVE:
                Tools.Toast(getApplicationContext(), "No possible move for player " + game.getActualPlayer().getName() + ",\nit's " + game.getEnemyPlayer().getName() + " turn");
                break;
            case Game.IMPOSSIBLE_MOVE:
                Tools.Toast(getApplicationContext(), "Move : " + nextMove.toString() + " is not possible !");
                break;
            case Game.END_GAME:
                Tools.Toast(getApplicationContext(), "Move : " + nextMove.toString() + " is not possible !");
                endGame();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveGame(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save_game) {
            saveGame(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /***
     * Get move with coords relative to the view supplied
     * @param view : is a ImageView (cell) in a rowView in a TableLayout
     * @return move coordinates
     */
    private Move getMoveFromCase(ImageView view){
        Move move = null;

        for (int j=0; j<tableLayout.getChildCount();j++){
            TableRow rowView = (TableRow) tableLayout.getChildAt(j);
            for(int i=0; i<rowView.getChildCount();i++){
                if(view.equals(rowView.getChildAt(i))){//verifier la comparaison
                    move = new Move(j, i);
                }
            }
        }

        return move;
    }

    /***
     * Get the ImageView (cell) from the given coordinates
     * @param row
     * @param col
     * @return
     */
    private ImageView getCaseView(int row, int col){
        ImageView imgView;

        //Get case view from coord.
        TableRow tableRow = (TableRow) tableLayout.getChildAt(row);
        imgView = (ImageView) tableRow.getChildAt(col);

        //if (view instanceof TableRow) //Peut etre mieux

        return imgView;
    }

    public void updateGraphic(){
        updateGraphicalBoard();
        updateScore();
    }

    private void updateGraphicalBoard(){
        ImageView imgView;

        for (int i = 0; i < GameBoard.BOARD_SIZE; i++) {
            for (int j = 0; j < GameBoard.BOARD_SIZE; j++) {
                int cellValue = game.getGameBoard().getPlayerIDAtPos(i, j);
                imgView = getCaseView(i, j);

                if (GameBoard.NO_COIN != cellValue) {
                    if(GameBoard.BLUE_COIN == cellValue) {
                        imgView.setImageResource(R.drawable.circle_blue);
                    }
                    else if(GameBoard.RED_COIN == cellValue){
                        imgView.setImageResource(R.drawable.circle_red);
                    }
                }
                else{
                    imgView.setImageDrawable(null);
                }
            }
        }
    }

    private void updateScore(){
        Player p1 = game.getPlayer(Game.PLAYER1);
        Player p2 = game.getPlayer(Game.PLAYER2);
        String text;

        if (game.isActualPlayer(p1)) {
            text = "<b><u>"+p1.getName() + "</u></b> " + p1.getScore() + scoreText + p2.getScore() + " <b>" + p2.getName() + "</b>";
        }
        else{
            text = "<b>"+p1.getName() + "</b> " + p1.getScore() + scoreText + p2.getScore() + " <b><u>" + p2.getName() + "</u></b>";
        }

        Spanned htmlText = Html.fromHtml(text);

        textScore.setText(htmlText);
    }

    /*      Sensors     */
    //http://stackoverflow.com/questions/2317428/android-i-want-to-shake-it

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        accelLast = accelCurrent;
        accelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
        float delta = accelCurrent - accelLast;
        accel = accel * 0.9f + delta; // perform low-cut filter

        if(accel > 12f){
            saveGame(true);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onDialogResult(MainDialog caller, Bundle result) {
        Intent intent;

        switch (result.getInt("newOrEnd")){
            case R.id.newGame:
                game = new Game(game.getMode(), game.getPlayer(Game.PLAYER1), game.getPlayer(Game.PLAYER2));
                updateGraphic();
                saveGame(false);
                break;
            case R.id.quit:
                intent = new Intent(GameActivity.this, HomeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public Player getNameWinner() {
        return game.getActualPlayer().getScore() > game.getEnemyPlayer().getScore() ? game.getActualPlayer() : game.getEnemyPlayer();
    }
}
