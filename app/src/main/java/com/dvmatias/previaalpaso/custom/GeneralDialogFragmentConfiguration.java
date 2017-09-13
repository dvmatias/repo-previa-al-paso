package com.dvmatias.previaalpaso.custom;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

/**
 * Created by dvmatias on 12/09/17.
 */

public final class GeneralDialogFragmentConfiguration {
    final Resources resources;
    static String title;
    static String message;
    static float width;
    static float height;

    private final static float MIN_WIDTH = 0.0f;
    private final static float MAX_WIDTH = 1.0f;
    private final static float MIN_HEIGHT = 0.0f;
    private final static float MAX_HEIGHT = 1.0f;

    private GeneralDialogFragmentConfiguration(final Builder builder) {
        resources = builder.context.getResources();
        // maxImageWidthForMemoryCache = builder.maxImageWidthForMemoryCache;

        // L.writeDebugLogs(builder.writeLogs);
    }

    /**
     * Creates default configuration for {@link GeneralDialogFragment} <br />
     * <b>Default values:</b>
     * <ul>
     * <li>maxImageWidthForMemoryCache = device's screen width</li>
     * </ul>
     */
    public static GeneralDialogFragmentConfiguration createDefault(Context context) {
        return new Builder(context).build();
    }

    public static class Builder {
        private Context context;

        public Builder(Context context) {
            this.context = context.getApplicationContext();
        }

        /**
         * <br/>Set title for the {@link GeneralDialogFragment}. <br/>
         *
         *@param title [String] Dialog title.</li>
         *
         */
        public Builder title(String title) {
            GeneralDialogFragmentConfiguration.title = title;
            return this;
        }

        /**
         * <br/>Set the body message for the  {@link GeneralDialogFragment}. <br/>
         *
         * @param message [String] Dialog body message.
         */
        public Builder message(String message) {
            GeneralDialogFragmentConfiguration.message = message;
            return this;
        }

        /**
         * <br/>Set the dialog CardView container width.<br/>
         * This param applies over the screen size width as a multiplier factor.<br/><br/>
         *
         * <b>NOTE: The value is a float number and must be between 0.0 and 1.0.</b><br/>
         *
         * @param width - [float] Percentage of the screen width (0.0 to 1.0).
         */
        public Builder width(float width) {
            if (width > MIN_WIDTH && width < MAX_WIDTH) {
                GeneralDialogFragmentConfiguration.width = width;
            }
            return this;
        }

        /**
         * <br/>Set the dialog CardView container width.<br/>
         * This param applies over the screen size width as a multiplier factor.<br/><br/>
         *
         * <b>NOTE: The value is a float number and must be between 0.0 and 1.0.</b>
         *
         * @param height [float] Percentage of the screen height (0.0 to 1.0).
         */
        public Builder height(float height) {
            if (height > MIN_HEIGHT && height < MAX_HEIGHT) {
                GeneralDialogFragmentConfiguration.height = height;
            }
            return this;
        }

        /** Builds configured {@link GeneralDialogFragmentConfiguration} object */
        public GeneralDialogFragmentConfiguration build() {
            initEmptyFieldsWithDefaultValues();
            return new GeneralDialogFragmentConfiguration(this);
        }

        private void initEmptyFieldsWithDefaultValues() {
            if (TextUtils.isEmpty(title)) {
                // TODO: make string resource
                title = "Dialog Title.";
            }
            if (TextUtils.isEmpty(message)) {
                // TODO: make string resource
                message = "Dialog Message.";
            }
        }
    }
}
