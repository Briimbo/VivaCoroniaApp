package de.tudarmstadt.iptk.foxtrot.vivacoronia.trading.models

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import de.tudarmstadt.iptk.foxtrot.vivacoronia.trading.supermarketInventory.SupermarketInventoryItemViewModel

class Supermarket(
    var supermarketId: String,
    var supermarketName: String,
    var supermarketLocation: LatLng,
    var inventory: List<InventoryItem>
) : Parcelable {
    var inventoryViewModel: List<SupermarketInventoryItemViewModel> = inventory.map { SupermarketInventoryItemViewModel(it) }

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(LatLng::class.java.classLoader)!!,
        parcel.createTypedArrayList(InventoryItem.CREATOR)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(supermarketId)
        parcel.writeString(supermarketName)
        parcel.writeParcelable(supermarketLocation, flags)
        parcel.writeTypedList(inventory)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Supermarket> {
        override fun createFromParcel(parcel: Parcel): Supermarket {
            return Supermarket(parcel)
        }

        override fun newArray(size: Int): Array<Supermarket?> {
            return arrayOfNulls(size)
        }
    }
}

class InventoryItem : Parcelable {
    var id: String
    var productCategory: String
    var supermarket: Supermarket? = null
    var availability: Int
    var itemName: String

    constructor(id: String,
                itemName: String,
                productCategory: String,
                availability: Int,
                supermarketId: String?,
                supermarketName: String?,
                supermarketLocation: LatLng?){
        this.id = id
        this.productCategory = productCategory
        this.itemName = itemName
        this.availability = availability
        if(!supermarketId.isNullOrEmpty() && !supermarketName.isNullOrEmpty() && supermarketLocation!=null){
            this.supermarket = Supermarket(supermarketId, supermarketName, supermarketLocation, listOf())
        }
    }

    constructor(parcel: Parcel): this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(LatLng::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(itemName)
        parcel.writeString(productCategory)
        parcel.writeInt(availability)
        parcel.writeString(supermarket?.supermarketId)
        parcel.writeString(supermarket?.supermarketName)
        parcel.writeParcelable(supermarket?.supermarketLocation, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InventoryItem> {
        override fun createFromParcel(parcel: Parcel): InventoryItem {
            return InventoryItem(parcel)
        }

        override fun newArray(size: Int): Array<InventoryItem?> {
            return arrayOfNulls(size)
        }
    }
}