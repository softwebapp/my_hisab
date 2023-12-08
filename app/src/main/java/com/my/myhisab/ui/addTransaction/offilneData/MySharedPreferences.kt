import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.my.myhisab.dto.UniqueIdDto
import com.my.myhisab.ui.addTransaction.OfflineTransactionDto

class MySharedPreferences(context: Context) {

    private val PREF_NAME = "MyPreferences"
    private val KEY_ARRAY_LIST = "myArrayList"

    private val Offline_LIST = "OfflineLIST"

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveArrayList(arrayList: List<OfflineTransactionDto>) {
        val editor = preferences.edit()
        val gson = Gson()
        val json = gson.toJson(arrayList)
        editor.putString(KEY_ARRAY_LIST, json)
        editor.apply()
    }

    fun getArrayList(): ArrayList<OfflineTransactionDto> {
        val gson = Gson()
        val json = preferences.getString(KEY_ARRAY_LIST, null)
        val type = object : TypeToken<ArrayList<OfflineTransactionDto>>() {}.type
        return gson.fromJson(json, type) ?: ArrayList()
    }

    fun addElement(element: OfflineTransactionDto) {
        val arrayList = getArrayList()
        arrayList.add(element)
        saveArrayList(arrayList)
    }

    fun removeElement(element: OfflineTransactionDto) {
        val arrayList = getArrayList()
        arrayList.remove(element)
        saveArrayList(arrayList)
    }

    fun removeElementById(idToRemove: Int) {
        val arrayList = getArrayList()
        val updatedList = arrayList.filter { it.id != idToRemove }
        saveArrayList(updatedList)
    }

    fun updateElement(index: Int, newElement: OfflineTransactionDto) {
        val arrayList = getArrayList()
        if (index in 0 until arrayList.size) {
            arrayList[index] = newElement
            saveArrayList(arrayList)
        }
    }


    fun saveOfflineUniqueArrayList(arrayList: ArrayList<UniqueIdDto.Data>) {
        val editor = preferences.edit()
        val gson = Gson()
        val json = gson.toJson(arrayList)
        editor.putString(Offline_LIST, json)
        editor.apply()
    }

    fun getOfflineUniqueArrayList(): ArrayList<UniqueIdDto.Data> {
        val gson = Gson()
        val json = preferences.getString(Offline_LIST, null)
        val type = object : TypeToken<ArrayList<UniqueIdDto.Data>>() {}.type
        return gson.fromJson(json, type) ?: ArrayList()
    }

}
