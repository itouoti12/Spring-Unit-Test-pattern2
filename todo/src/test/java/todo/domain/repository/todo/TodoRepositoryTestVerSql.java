package todo.domain.repository.todo;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import todo.domain.model.Todo;

/**
 * Repository Test
 * @sqlによるデータのセットアップ
 * jdbctemplateによる比較
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/test-context.xml"})
@Transactional
public class TodoRepositoryTestVerSql {

	@Inject
	TodoRepository target;
	
	@Inject
	JdbcTemplate jdbctemplate;
	
	@Before
	public void setUp() throws Exception {
		//@sqlアノテーションで指定したsqlファイルによってセットアップを実行するため、処理なし
	}
	
	@Test
	@Rollback
	@Sql("classpath:database/test_data.sql")
	public void testUpdate() throws Exception {
		//テスト用のデータを作成
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String todoId = "cceae402-c5b1-440f-bae2-7bee19dc17fb";
		Todo testDataTodo = getTodoData(todoId);
		testDataTodo.setFinished(true);
		
		//updateメソッドのテスト
		boolean actTodo = target.update(testDataTodo);
		
		//結果検証
		assertEquals(actTodo, true);
		
		//期待値の作成
		Todo exptodo = new Todo();
		exptodo.setTodoId("cceae402-c5b1-440f-bae2-7bee19dc17fb");
		exptodo.setTodoTitle("one");
		exptodo.setFinished(true);
		String strDate = "2017-10-01 15:39:17.888";
		Date date = sdFormat.parse(strDate);
		exptodo.setCreatedAt(date);
		
		//処理後データの取得
		Todo actTestDataTodo = getTodoData(todoId);
		
		//メソッド実行後テーブルデータ検証
		//date型の表示形式が異なるため、時刻文字列に変換して比較している
		assertEquals(exptodo.getTodoId(), actTestDataTodo.getTodoId());
		assertEquals(exptodo.getTodoTitle(), actTestDataTodo.getTodoTitle());
		assertEquals(exptodo.isFinished(), actTestDataTodo.isFinished());
		assertEquals(sdFormat.format(exptodo.getCreatedAt()) ,sdFormat.format(actTestDataTodo.getCreatedAt()));
		
		
	}
	
	//テスト用元データの取得
	private Todo getTodoData(String todoId) {
		
		String sql = "SELECT * FROM todo WHERE todo_id=?";
		
		Todo todoData = (Todo)jdbctemplate.queryForObject(sql, new Object[] {todoId},
				new RowMapper<Todo>() {
					public Todo mapRow(ResultSet rs, int rownum) throws SQLException {
						Todo todoSql = new Todo();
						
						todoSql.setTodoId(rs.getString("todo_id"));
						todoSql.setTodoTitle(rs.getString("todo_title"));
						todoSql.setFinished(rs.getBoolean("finished"));
						todoSql.setCreatedAt(rs.getTimestamp("created_at"));
					
						return todoSql;
					}
		});
		return todoData;
	}
}
