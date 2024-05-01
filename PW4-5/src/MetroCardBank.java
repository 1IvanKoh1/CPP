import java.util.ArrayList;

public class MetroCardBank {
    private ArrayList<MetroCard> store;

    public MetroCardBank() {
        this.store = new ArrayList<>();
    }

    public ArrayList<MetroCard> getStore() {
        return store;
    }

    public void setStore(ArrayList<MetroCard> store) {
        this.store = store;
    }
    public int findMetroCard(String serNum) {
        for (int i = 0; i < store.size(); i++) {
            if (store.get(i).getSerNum().equals(serNum)) {
                return i;
            }
        }
        return -1;
    }

    public int numCards() {
        return store.size();
    }

    public void addCard(MetroCard newCard) {
        store.add(newCard);
    }

    public boolean removeCard(String serNum) {
        int index = findMetroCard(serNum);
        if (index != -1) {
            store.remove(index);
            return true;
        }
        return false;
    }

    public boolean addMoney(String serNum, double money) {
        int index = findMetroCard(serNum);
        if (index != -1) {
            MetroCard card = store.get(index);
            card.setBalance(card.getBalance() + money);
            return true;
        }
        return false;
    }

    public boolean getMoney(String serNum, double money) {
        int index = findMetroCard(serNum);
        if (index != -1) {
            MetroCard card = store.get(index);
            if (card.getBalance() >= money) {
                card.setBalance(card.getBalance() - money);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("List of MetroCards:");
        for (MetroCard c : store) {
            buf.append("\n\n").append(c);
        }
        return buf.toString();
    }
}
