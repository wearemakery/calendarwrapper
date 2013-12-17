package com.inch.android.sandbox.calendar;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andraskindler on 14/12/13.
 */
public class Attendee {

    public Integer id;
    public Integer eventID;
    public String name;
    public String email;
    public Integer type;
    public Integer relationship;
    public Integer status;

    public ContentValues mapToContentValues() {
        final ContentValues contentValues = new ContentValues();
        if (id != null) contentValues.put(CalendarContract.Attendees._ID, id);
        if (eventID != null) contentValues.put(CalendarContract.Attendees.EVENT_ID, eventID);
        if (name != null) contentValues.put(CalendarContract.Attendees.ATTENDEE_NAME, name);
        if (email != null) contentValues.put(CalendarContract.Attendees.ATTENDEE_EMAIL, email);
        if (type != null) contentValues.put(CalendarContract.Attendees.ATTENDEE_TYPE, type);
        if (relationship != null) contentValues.put(CalendarContract.Attendees.ATTENDEE_RELATIONSHIP, relationship);
        if (status != null) contentValues.put(CalendarContract.Attendees.ATTENDEE_STATUS, status);
        return contentValues;
    }

    public int delete(final ContentResolver contentResolver) {
        return contentResolver.delete(ContentUris.withAppendedId(CalendarContract.Attendees.CONTENT_URI, id), null, null);
    }

    public int addToEvent(final ContentResolver contentResolver, final Event event) {
        eventID = event.id;
        final Uri uri = contentResolver.insert(CalendarContract.Attendees.CONTENT_URI, mapToContentValues());
        return id = Integer.parseInt(uri.getLastPathSegment());
    }

    public static List<Attendee> getAttendeesForQuery(final String query, final String[] queryArgs, final String sortOrder, final ContentResolver contentResolver) {
        final String[] attendeeProjection = new String[]{
            CalendarContract.Attendees._ID,
            CalendarContract.Attendees.EVENT_ID,
            CalendarContract.Attendees.ATTENDEE_NAME,
            CalendarContract.Attendees.ATTENDEE_EMAIL,
            CalendarContract.Attendees.ATTENDEE_TYPE,
            CalendarContract.Attendees.ATTENDEE_RELATIONSHIP,
            CalendarContract.Attendees.ATTENDEE_STATUS
        };

        final Cursor cursor = contentResolver.query(CalendarContract.Attendees.CONTENT_URI, attendeeProjection, query, queryArgs, sortOrder);

        final List<Attendee> attendees = new ArrayList<Attendee>();
        while (cursor.moveToNext()) {
            final Attendee attendee = new Attendee();
            attendee.id = cursor.getInt(0);
            attendee.eventID = cursor.getInt(1);
            attendee.name = cursor.getString(2);
            attendee.email = cursor.getString(3);
            attendee.type = cursor.getInt(4);
            attendee.relationship = cursor.getInt(5);
            attendee.status = cursor.getInt(6);
            attendees.add(attendee);
        }

        return attendees;
    }

}
