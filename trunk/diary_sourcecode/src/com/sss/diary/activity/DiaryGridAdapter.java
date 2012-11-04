package com.sss.diary.activity;

import java.util.ArrayList;

import com.sss.diary.R;
import com.sss.diary.domain.DiaryContentInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DiaryGridAdapter extends BaseAdapter {
	
	private Context mContext;
	
	private ArrayList<DiaryContentInfo> lstDiaryInfo;
	
	public DiaryGridAdapter(Context context, ArrayList<DiaryContentInfo> infos){
		this.mContext = context;
		this.lstDiaryInfo = infos;
	}

	@Override
	public int getCount() {
		
		return this.lstDiaryInfo.size();
	}

	@Override
	public Object getItem(int position) {
		
		return this.lstDiaryInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.lstDiaryInfo.get(position).hashCode();
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		
		if(convertView == null){   
            convertView = LayoutInflater.from(mContext).inflate(R.layout.diary_grid_item, null);   
            DiaryGridViewCache viewCache = new DiaryGridViewCache();
            viewCache.imgCenter = (ImageView)convertView.findViewById(R.id.img_diary_item_center);
            viewCache.linearMain = (LinearLayout)convertView.findViewById(R.id.linear_diary_item_main);
            viewCache.tvTitle = (TextView)convertView.findViewById(R.id.tv_diary_item_title);
            viewCache.tvContent = (TextView)convertView.findViewById(R.id.tv_diary_item_content);
            convertView.setTag(viewCache);
        }   
		DiaryGridViewCache cache = (DiaryGridViewCache)convertView.getTag();   
		if(pos!=0){
			//设置显示内容
			cache.imgCenter.setVisibility(View.GONE);
			cache.linearMain.setVisibility(View.VISIBLE);
			DiaryContentInfo diary = lstDiaryInfo.get(pos);
			if(diary!=null){
				cache.tvTitle.setText(diary.getTitle());
				cache.tvContent.setText(diary.getContent());
			}else{
				throw new NullPointerException("DiaryGridAdapter -- getView: List["+ pos +"] is null!");
			}
			
		}else{
			//第一个显示新增图标
			cache.linearMain.setVisibility(View.GONE);
			cache.imgCenter.setVisibility(View.VISIBLE);
			cache.imgCenter.setImageResource(R.drawable.note_new);
		}
			
        
        return convertView;
	}

	
	private static class DiaryGridViewCache{
		public ImageView imgCenter;
		public LinearLayout linearMain;
    	public TextView tvTitle;
    	public TextView tvContent;
    } 
}
