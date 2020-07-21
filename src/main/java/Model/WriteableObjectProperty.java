package Model;

import javafx.beans.property.SimpleObjectProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class WriteableObjectProperty<T> extends SimpleObjectProperty<T> implements Serializable {

    public WriteableObjectProperty () {
        super();
    }

    public WriteableObjectProperty (T obj) {
        super(obj);
    }


    private void writeObject (ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(get());
    }

    private void readObject (ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        set((T) s.readObject());
    }
}