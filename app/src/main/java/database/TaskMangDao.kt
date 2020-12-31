package database
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newtaskapp.Task

@Dao
interface TaskMangDao {
    @Query("SELECT * FROM Task")
       fun getTasks(): LiveData<List<Task>>
    @Query("SELECT * FROM Task WHERE id=(:id)")
      fun getTask(id: Int): LiveData<Task?>
    @Query("SELECT * FROM Task WHERE status_task=1")
    fun getTodo():LiveData<List<Task>>
    @Query("SELECT * FROM Task WHERE status_task=2")
    fun getPrograss():LiveData<List<Task>>
    @Query("SELECT * FROM Task WHERE status_task=3")
    fun getDone(): LiveData<List<Task>>

  @Update
    fun updateTask(taskMang : Task)
    @Insert
    fun addTask(taskMang : Task)
    @Delete
    fun deleteTask(taskMang : Task)

}