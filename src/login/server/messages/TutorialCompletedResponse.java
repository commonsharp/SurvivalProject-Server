package login.server.messages;

import login.server.ServerGenericMessage;

public class TutorialCompletedResponse extends ServerGenericMessage {
	// big...
	int itemTypes[]; // 10
	int zeros[]; //10 // skills? just guessing
	int itemRemainingForceDays[]; // 10;
	int unk2; // -1
	int unk3; //1005
	int unk4; //1243904
	
	public class Item {
		int uniqueId;
	    int type;
	    int remainingForceDays;
	    int levelOrRemainingUses;
	    int skills;
	    
		void SetItem(int uniqueId, int type, int remainingForceDays, int levelOrRemainingUses, int skills)
		{
			this.uniqueId = uniqueId;
		    this.type = type;
		    this.remainingForceDays = remainingForceDays;
		    this.levelOrRemainingUses = levelOrRemainingUses;
		    this.skills = skills;
		}
		
		public int GetType() {
			return type;
		}
		
		public int GetRemainingForceDays() {
			return remainingForceDays;
		}
	}
	
	public TutorialCompletedResponse() {
		super(0x2923);
		
		final int t_items[] = {11204, 11203, 11202, 11201, 11101, 11102, 11103, 11104, 11301, 2910};
		
		Item items[] = new Item[10];
		
		int r = (int)(Math.random() * 5) + 1;
		for (int i = 0; i < 9; i++)
		{
			int Type = t_items[i] + (r*10);
			items[i] = new Item();
			items[i].SetItem(i,Type,100,1,0);
		}
		items[9] = new Item();
		items[9].SetItem(9,t_items[9]+r,30,1,0);
		
		itemTypes = new int[10];
		zeros = new int[10];
		itemRemainingForceDays = new int[10];
		
		for (int i = 0; i < 10; i++)
		{
			itemTypes[i] = items[i].GetType();
			itemRemainingForceDays[i] = items[i].GetRemainingForceDays();
			zeros[i] = 0;
		}
		unk2 = -1;
		unk3 = 2000;
		unk4 = 0;
	}

	@Override
	public void changeData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPayload() {
		payload.putIntArray(itemTypes);
		payload.putIntArray(zeros);
		payload.putIntArray(itemRemainingForceDays);
		payload.putInteger(unk2);
		payload.putInteger(unk3);
		payload.putInteger(unk4);
		
	}

}