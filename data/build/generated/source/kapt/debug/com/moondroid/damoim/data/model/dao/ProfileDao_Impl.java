package com.moondroid.damoim.data.model.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.moondroid.damoim.data.model.entity.ProfileEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProfileDao_Impl implements ProfileDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ProfileEntity> __insertionAdapterOfProfileEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteProfileAll;

  public ProfileDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProfileEntity = new EntityInsertionAdapter<ProfileEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `profile` (`id`,`name`,`birth`,`gender`,`location`,`interest`,`thumb`,`message`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @Nullable final ProfileEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getBirth() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getBirth());
        }
        if (entity.getGender() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getGender());
        }
        if (entity.getLocation() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getLocation());
        }
        if (entity.getInterest() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getInterest());
        }
        if (entity.getThumb() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getThumb());
        }
        if (entity.getMessage() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getMessage());
        }
      }
    };
    this.__preparedStmtOfDeleteProfileAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM profile";
        return _query;
      }
    };
  }

  @Override
  public Object insertProfile(final ProfileEntity profile,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProfileEntity.insert(profile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteProfileAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteProfileAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteProfileAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getProfileList(final Continuation<? super List<ProfileEntity>> $completion) {
    final String _sql = "SELECT * FROM profile";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ProfileEntity>>() {
      @Override
      @NonNull
      public List<ProfileEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfBirth = CursorUtil.getColumnIndexOrThrow(_cursor, "birth");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfInterest = CursorUtil.getColumnIndexOrThrow(_cursor, "interest");
          final int _cursorIndexOfThumb = CursorUtil.getColumnIndexOrThrow(_cursor, "thumb");
          final int _cursorIndexOfMessage = CursorUtil.getColumnIndexOrThrow(_cursor, "message");
          final List<ProfileEntity> _result = new ArrayList<ProfileEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ProfileEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpBirth;
            if (_cursor.isNull(_cursorIndexOfBirth)) {
              _tmpBirth = null;
            } else {
              _tmpBirth = _cursor.getString(_cursorIndexOfBirth);
            }
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final String _tmpInterest;
            if (_cursor.isNull(_cursorIndexOfInterest)) {
              _tmpInterest = null;
            } else {
              _tmpInterest = _cursor.getString(_cursorIndexOfInterest);
            }
            final String _tmpThumb;
            if (_cursor.isNull(_cursorIndexOfThumb)) {
              _tmpThumb = null;
            } else {
              _tmpThumb = _cursor.getString(_cursorIndexOfThumb);
            }
            final String _tmpMessage;
            if (_cursor.isNull(_cursorIndexOfMessage)) {
              _tmpMessage = null;
            } else {
              _tmpMessage = _cursor.getString(_cursorIndexOfMessage);
            }
            _item = new ProfileEntity(_tmpId,_tmpName,_tmpBirth,_tmpGender,_tmpLocation,_tmpInterest,_tmpThumb,_tmpMessage);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getProfile(final Continuation<? super ProfileEntity> $completion) {
    return ProfileDao.DefaultImpls.getProfile(ProfileDao_Impl.this, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
