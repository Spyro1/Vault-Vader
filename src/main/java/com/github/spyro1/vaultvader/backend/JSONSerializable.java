package com.github.spyro1.vaultvader.backend;

import org.json.simple.JSONObject;
/**
 * Interface representing an object that can be serialized to and deserialized from JSON.
 * Implementing classes are expected to provide methods for converting their instances
 * to a JSON object and for populating their state from a JSON object.
 */
public interface JSONSerializable {

    /**
     * Serializes the current instance to a JSON object.
     * @return A {@link JSONObject} representing the current object's state in JSON format.
     */
    JSONObject toJSON();

    /**
     * Populates the current instance's state from the given JSON object.
     * @param json A {@link JSONObject} containing the data to populate the object's state.
     * @return The object itself after populating its state from the JSON object.
     *         This allows for method chaining.
     */
    Object fromJSON(JSONObject json);
}

