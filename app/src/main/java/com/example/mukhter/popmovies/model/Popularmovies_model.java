package com.example.mukhter.popmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MUKHTER on 02/04/2017.
 */

public class Popularmovies_model implements Parcelable {
    public String image;
    public String originaltitle;
    public String plotsynopsis;
    public String userrating;
    public String releasedate;
    public String backdrop;
    public String id;

    public Popularmovies_model() {
        super();
    }

    private Popularmovies_model(Parcel parcel) {
        image = parcel.readString();
        originaltitle = parcel.readString();
        plotsynopsis = parcel.readString();
        userrating = parcel.readString();
        releasedate = parcel.readString();
        backdrop=parcel.readString();
        id =parcel.readString();
    }

    public static final Creator<Popularmovies_model> CREATOR = new Creator<Popularmovies_model>() {
        @Override
        public Popularmovies_model createFromParcel(Parcel in) {
            return new Popularmovies_model(in);
        }

        @Override
        public Popularmovies_model[] newArray(int size) {
            return new Popularmovies_model[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOriginaltitle() {
        return originaltitle;
    }

    public void setOriginaltitle(String originaltitle) {
        this.originaltitle = originaltitle;
    }

    public String getPlotsynopsis() {
        return plotsynopsis;
    }

    public void setPlotsynopsis(String plotsynopsis) {
        this.plotsynopsis = plotsynopsis;
    }

    public String getUserrating() {
        return userrating;
    }

    public void setUserrating(String userrating) {
        this.userrating = userrating;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(image);
        parcel.writeString(originaltitle);
        parcel.writeString(plotsynopsis);
        parcel.writeString(userrating);
        parcel.writeString(releasedate);
        parcel.writeString(backdrop);
        parcel.writeString(id);
    }
}
