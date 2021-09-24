package free.imei.check.and.network.unlocker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import free.imei.check.and.network.unlocker.Models.ModelNames;
import free.imei.check.and.network.unlocker.R;

public class AutoCompleteModelAdapter extends ArrayAdapter<ModelNames> {

    private List<ModelNames> modelNamesList;

    public AutoCompleteModelAdapter(@NonNull Context context, @NonNull List<ModelNames> modelNames) {
        super(context, 0, modelNames);
        modelNamesList = new ArrayList<>(modelNames);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return modelFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.model_autocomplete_row, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.text_view_name);
        ImageView imageViewFlag = convertView.findViewById(R.id.image_view_flag);

        ModelNames modelName = getItem(position);

        if (modelName != null) {
            textViewName.setText(modelName.getModelName());
            imageViewFlag.setImageResource(modelName.getModelImage());
        }

        return convertView;
    }

    private Filter modelFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ModelNames> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(modelNamesList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ModelNames item : modelNamesList) {
                    if (item.getModelName().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((List) filterResults.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((ModelNames) resultValue).getModelName();
        }
    };
}
