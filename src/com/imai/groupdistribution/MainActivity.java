package com.imai.groupdistribution;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends Activity implements OnClickListener {
	// 全生徒のデータ
	private static final String[] allNameTxt = {
			"安達誠寛", "一瀬孝", "今井俊介", "岩崎大輔", "岩崎拓也",
			"岩塚美由紀", "大司まり", "大津良馬", "嘉村翼", "神田圭司",
			"楠元信吾", "桑原玲", "小西未央子", "佐藤章", "柴田久美子",
			"大力新太郎", "高倉健治", "壇義弘", "能島章典", "野田幸代",
			"松島あゆみ", "松本真由美", "山口徹", "山本康平" };
	// 振り分け人数候補
	private static final int[] GROUP_PEOPLE = {
		2,3,4,5,6,7,8,9,10};
	
	// 設定した人数
	private int setGroupPeople = 2;
	
	private ArrayList<String> allNameList = new ArrayList<String>();

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		listView = (ListView) findViewById(R.id.listView1);
		
		Button button = (Button) findViewById(R.id.button1);

		for (int i = 0; i < allNameTxt.length; i++) {
			allNameList.add(allNameTxt[i]);

		}

		// チェックボックス付きのアダプタを作成
		ArrayAdapter<String> adapter =
				new ArrayAdapter<String>(
						this,
						android.R.layout.simple_list_item_checked,
						allNameList
				);
		
		// 選択方式の設定
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		// アイテムがクリックされた時に呼び出されるコールバックを登録
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {

			}
		});
		
		// アダプターを設定します
		listView.setAdapter(adapter);
		
		/* spenerの表示 ここから */
		// spener表示の為のAdapter
		ArrayAdapter<String> spenerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		spenerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 設定人数を追加
		for (int i = 0; i < GROUP_PEOPLE.length; i++) {
			spenerAdapter.add(GROUP_PEOPLE[i] +"");
		}

		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		// アダプターを設定
		spinner.setAdapter(spenerAdapter);
		/* spenerの表示 ここまで */
		
		// スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Spinner spinner = (Spinner) parent;
				// 選択されたspinnerのIDを取得
				int itemId = (int) spinner.getSelectedItemId();
				setGroupPeople = GROUP_PEOPLE[itemId];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
			
		

		button.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		ArrayList<Integer> noNameNumberList = new ArrayList<Integer>();
		// チェックしたアイテムを取得
		SparseBooleanArray checked = listView.getCheckedItemPositions();
		for (int i = 0; i < checked.size(); i++) {
			if(checked.valueAt(i)){
				int key = checked.keyAt(i);
				noNameNumberList.add(key);
			}
		}
		
		Intent intent = new Intent(MainActivity.this, GroupActivity.class);
		// 生徒名を渡す
		intent.putStringArrayListExtra("list", allNameList);
		// 欠席した生徒の番号を渡す
		intent.putIntegerArrayListExtra("noNameNumber", noNameNumberList);
		// 設定した人数を渡す
		intent.putExtra("setGroupPeple", setGroupPeople);
		startActivity(intent);
	}
}
