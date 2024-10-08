package com.github.spyro1.vaultvader.api;

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
     * @throws Exception if the given argument does not contain all needed key-value paris.
     */
    Object fromJSON(JSONObject json) throws Exception;

}

