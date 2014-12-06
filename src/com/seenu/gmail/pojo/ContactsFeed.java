package com.seenu.gmail.pojo;

import java.util.ArrayList;

public class ContactsFeed {

	public Feed feed;

	public Feed getFeed() {
		return feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}

	public static class Feed {

		public Id id;
		public Title title;
		public TotalResults openSearch$totalResults;
		public ArrayList<Entry> entry;

		public Feed() {
			super();
			// TODO Auto-generated constructor stub

			entry = new ArrayList<Entry>();
		}

		public Id getId() {
			return id;
		}

		public void setId(Id id) {
			this.id = id;
		}

		public Title getTitle() {
			return title;
		}

		public void setTitle(Title title) {
			this.title = title;
		}

		public TotalResults getOpenSearch$totalResults() {
			return openSearch$totalResults;
		}

		public void setOpenSearch$totalResults(
				TotalResults openSearch$totalResults) {
			this.openSearch$totalResults = openSearch$totalResults;
		}

		public ArrayList<Entry> getEntry() {
			return entry;
		}

		public void setEntry(ArrayList<Entry> entry) {
			this.entry = entry;
		}

		// class for ID
		public static class Id {

			public String $t;

			public String get$t() {
				return $t;
			}

			public void set$t(String $t) {
				this.$t = $t;
			}

		}

		// class for Total Results
		public static class TotalResults {

			public String $t;

			public String get$t() {
				return $t;
			}

			public void set$t(String $t) {
				this.$t = $t;
			}

		}

		// each contacts class
		public static class Entry {

			public Title title;
			public ArrayList<Email> gd$email;
			public ArrayList<PhoneNumber> gd$phoneNumber;

			public Entry() {
				super();
				// TODO Auto-generated constructor stub

				gd$email = new ArrayList<Email>();
				gd$phoneNumber = new ArrayList<PhoneNumber>();
			}

			public Title getTitle() {
				return title;
			}

			public void setTitle(Title title) {
				this.title = title;
			}

			public ArrayList<Email> getGd$email() {
				return gd$email;
			}

			public void setGd$email(ArrayList<Email> gd$email) {
				this.gd$email = gd$email;
			}

			public ArrayList<PhoneNumber> getGd$phoneNumber() {
				return gd$phoneNumber;
			}

			public void setGd$phoneNumber(ArrayList<PhoneNumber> gd$phoneNumber) {
				this.gd$phoneNumber = gd$phoneNumber;
			}

			public static class Email {

				public String address;
				public boolean primary;

				public String getAddress() {
					return address;
				}

				public void setAddress(String address) {
					this.address = address;
				}

				public boolean isPrimary() {
					return primary;
				}

				public void setPrimary(boolean primary) {
					this.primary = primary;
				}

			}

			public static class PhoneNumber {

				public String $t;
				public boolean primary;

				public String get$t() {
					return $t;
				}

				public void set$t(String $t) {
					this.$t = $t;
				}

				public boolean isPrimary() {
					return primary;
				}

				public void setPrimary(boolean primary) {
					this.primary = primary;
				}

			}

		}

	}

}
