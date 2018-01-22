package com.hykj.myviewlib.tree;

import java.util.ArrayList;
import java.util.List;

import com.hykj.myviewlib.R;
import com.hykj.myviewlib.tree.widget.BaseTreeViewAdapter;
import com.hykj.myviewlib.tree.widget.TreeView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * TreeView demo adapter
 *
 * @author markmjw
 * @date 2014-01-04
 */
@SuppressLint("InflateParams")
public class TreeViewAdapter<T extends TreeItem<T>> extends BaseTreeViewAdapter {
    private LayoutInflater mInflater;
    
    private List<T> dataList = new ArrayList<T>();

    public TreeViewAdapter(Context context, TreeView treeView) {
        super(treeView);
        mInflater = LayoutInflater.from(context);
    }
    
    public void setDataList(List<T> dataList){
    	this.dataList = dataList;
    }
    
    public List<T> getDataList(){
    	return dataList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
    	if(dataList == null || dataList.size() == 0)
    		return null;
    	
        return dataList.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
    	if(dataList == null || dataList.size() == 0)
    		return 0;
    	
        return dataList.get(groupPosition).getChildren().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
    	if(dataList == null || dataList.size() == 0)
    		return 0;
    	
        return dataList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
    	if(dataList == null || dataList.size() == 0)
    		return 0;
    	
        return dataList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
    	
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_view, null);
        }

        final ChildHolder holder = getChildHolder(convertView);
        Object item = getChild(groupPosition, childPosition);
        holder.name.setText(item.toString());
        int visible = View.GONE;
        if(lastSelectedItem != null && item == lastSelectedItem){
        	visible = View.VISIBLE;
        }
        holder.selected.setVisibility(visible);
        return convertView;
    }

    private ChildHolder getChildHolder(final View view) {
        ChildHolder holder = (ChildHolder) view.getTag();
        if (null == holder) {
            holder = new ChildHolder(view);
            view.setTag(holder);
        }
        return holder;
    }

    private class ChildHolder {
        TextView name;
        TextView selected;

        public ChildHolder(View view) {
            name = (TextView) view.findViewById(R.id.contact_list_item_name);
            selected = (TextView) view.findViewById(R.id.list_item_selected);
        }
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {
    	final T item = dataList.get(groupPosition);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_group_view, null);
        }

        GroupHolder holder = getGroupHolder(convertView);

        holder.name.setText(item.toString());
        holder.onlineNum.setText(getChildrenCount(groupPosition) + "/" + getChildrenCount(groupPosition));
        if (isExpanded) {
            holder.indicator.setImageResource(R.drawable.indicator_expanded);
        } else {
            holder.indicator.setImageResource(R.drawable.indicator_unexpanded);
        }

        return convertView;
    }

    private GroupHolder getGroupHolder(final View view) {
        GroupHolder holder = (GroupHolder) view.getTag();
        if (null == holder) {
            holder = new GroupHolder(view);
            view.setTag(holder);
        }
        return holder;
    }

    private class GroupHolder {
        TextView name;
        ImageView indicator;
        TextView onlineNum;

        public GroupHolder(View view) {
            name = (TextView) view.findViewById(R.id.group_name);
            indicator = (ImageView) view.findViewById(R.id.group_indicator);
            onlineNum = (TextView) view.findViewById(R.id.online_count);
        }
    }

    @Override
    public void updateHeader(View header, int groupPosition, int childPosition, int alpha) {
        ((TextView) header.findViewById(R.id.group_name)).setText(dataList.get(groupPosition).toString());
        ((TextView) header.findViewById(R.id.online_count)).setText(getChildrenCount
                (groupPosition) + "/" + getChildrenCount(groupPosition));
        header.setAlpha(alpha);
    }
}
